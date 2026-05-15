//
//  AppCoordinator.swift
//  iosApp
//
//  Created by Taki Enari on 2026/05/11.
//
import Di
import UIKit

@MainActor
class AppCoordinator: ObservableObject {
    private let resolveStartDestinationUseCase = getResolveStartDestinationUseCase()
    let appRouter = getAppRouter()
    weak var navigationController: UINavigationController?

    func subscribeToNavigationEvents() async {
        for await navEvent in appRouter.events {
            switch onEnum(of: navEvent) {
            case .goBack:
                navigationController?.popViewController(animated: true)
            case .goTo(let navigationEvent):
                guard let vc = makeViewController(for: navigationEvent.route) else { return }
                navigationController?.pushViewController(vc, animated: true)
            }
        }
    }

    func resolveInitialDestination() async {
        let startRoute = try? await resolveStartDestinationUseCase.execute()
        let vc = startRoute.flatMap { route in
            makeViewController(for: route)
        } ?? authLandingScreenViewController()
        navigationController?.setViewControllers([vc], animated: false)
    }

    private func makeViewController(for route: any AppRoute) -> UIViewController? {
        if route is OnboardingRoutesAuth { return authScreenViewController() }
        if route is OnboardingRoutesProfileSetup { return nil } // TODO
        if route is Home { return nil } // TODO
        return nil
    }
}

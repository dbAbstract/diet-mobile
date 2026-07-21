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
                if navigationController?.presentedViewController != nil {
                    navigationController?.dismiss(animated: true)
                } else {
                    navigationController?.popViewController(animated: true)
                }
            case .goTo(let navigationEvent):
                guard let vc = makeViewController(for: navigationEvent.route) else { return }
                if navigationEvent.route is ModalRoute {
                    vc.modalPresentationStyle = .pageSheet
                    if let sheet = vc.sheetPresentationController {
                        sheet.prefersGrabberVisible = true
                    }
                    navigationController?.present(vc, animated: true)
                } else {
                    navigationController?.pushViewController(vc, animated: true)
                }
            case .clearBackStackAndGoTo(let navigationEvent):
                guard let vc = makeViewController(for: navigationEvent.route) else { return }
                navigationController?.dismiss(animated: false)
                navigationController?.setViewControllers([vc], animated: true)
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
        if route is OnboardingRoutesAuth.Landing { return authLandingScreenViewController() }
        if route is OnboardingRoutesAuth.SignIn { return signInScreenViewController() }
        if route is OnboardingRoutesAuth.SignUp { return signUpScreenViewController() }
        if route is OnboardingRoutesProfileSetup { return profileSetupScreenViewController() }
        if route is Home { return nil } // TODO: Home screen
        return nil
    }
}

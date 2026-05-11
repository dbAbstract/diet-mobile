import SwiftUI
import Di

struct ContentView: View {
    @ObservedObject var coordinator: AppCoordinator

    var body: some View {
        RootNavigationView(coordinator: coordinator)
            .ignoresSafeArea()
            .task { await coordinator.resolveInitialDestination() }
            .task { await coordinator.subscribeToNavigationEvents() }
    }
}

private struct RootNavigationView: UIViewControllerRepresentable {
    let coordinator: AppCoordinator

    func makeUIViewController(context: Context) -> UINavigationController {
        let navController = UINavigationController()
        coordinator.navigationController = navController
        return navController
    }

    func updateUIViewController(_ uiViewController: UINavigationController, context: Context) {}
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView(coordinator: AppCoordinator())
    }
}

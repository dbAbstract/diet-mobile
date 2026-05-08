import SwiftUI
import Di

// ── App coordinator ───────────────────────────────────────────────────────────
// Equivalent of MainViewModel on Android.
// @MainActor pins all @Published mutations to the main thread, same as
// how Android's StateFlow.value updates land on Dispatchers.Main in Compose.

@MainActor
private class AppCoordinator: ObservableObject {

    // nil == "not resolved yet" — mirrors the initialValue = null in StateFlow
    @Published var destination: AppDestination?

    func resolveDestination() async {
        let useCase = getResolveAppStateUseCase()

        // SKIE turns suspend fun into async throws.
        // try? mirrors the fact that Android's coroutine scope swallows errors silently here.
        guard let state = try? await useCase.execute() else {
            destination = .auth
            return
        }

        // onEnum(of:) is SKIE's exhaustive switch helper for Kotlin sealed interfaces.
        // Android analogy: it's the Swift equivalent of a Kotlin `when (state)` expression.
        // The case labels are camelCase versions of the Kotlin subclass names.
        switch onEnum(of: state) {
        case .fullySetup:
            destination = .home
        case .requiresLogin:
            destination = .auth
        case .requiresProfileSetup:
            destination = .profileSetup
        }
    }
}

private enum AppDestination {
    case auth, home, profileSetup
}

// ── Root view ─────────────────────────────────────────────────────────────────
// Mirrors the setContent { } block in MainActivity.

struct ContentView: View {

    // @StateObject ≈ viewModel() in Compose — one instance, survives recomposition
    @StateObject private var coordinator = AppCoordinator()

    var body: some View {
        Group {
            switch coordinator.destination {
            case .auth:
                AuthComposeView()
            case .home, .profileSetup:
                // TODO: implement home / profile-setup screens
                Text("Coming soon")
            case .none:
                // Holds a blank screen while the use case resolves,
                // same role as splashScreen.setKeepOnScreenCondition on Android.
                Color(.systemBackground).ignoresSafeArea()
            }
        }
        // .task { } ≈ LaunchedEffect(Unit) — fires once on first appearance
        .task {
            await coordinator.resolveDestination()
        }
    }
}

// ── CMP screen bridge ─────────────────────────────────────────────────────────
// UIViewControllerRepresentable ≈ AndroidView { } — wraps a UIViewController
// (the ComposeUIViewController produced by authScreenViewController()) inside
// a SwiftUI view so NavigationStack can treat it like any other SwiftUI screen.

private struct AuthComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        authScreenViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

// ── Preview ───────────────────────────────────────────────────────────────────

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}

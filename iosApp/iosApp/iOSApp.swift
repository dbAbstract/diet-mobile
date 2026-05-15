import SwiftUI
import Di
import FirebaseCore

@main
struct iOSApp: App {
    @StateObject private var coordinator = AppCoordinator()
    
    init() {
        startKoin()
        FirebaseApp.configure()
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView(coordinator: self.coordinator)
        }
    }
}

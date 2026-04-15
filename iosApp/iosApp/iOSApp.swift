import SwiftUI
import Di
import FirebaseCore

@main
struct iOSApp: App {
    init() {
        startKoin()
        FirebaseApp.configure()
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}

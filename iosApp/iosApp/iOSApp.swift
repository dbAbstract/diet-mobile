import SwiftUI
import Di

@main
struct iOSApp: App {
    init() {
        startKoin()
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}

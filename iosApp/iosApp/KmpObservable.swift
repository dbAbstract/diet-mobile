import Foundation

/// Wraps a KMP ViewModel-like object as a SwiftUI ObservableObject.
/// The onDeinit closure is called when this holder is deallocated,
/// which is where you call kmp.onCleared() to cancel the coroutine scope.
@MainActor
final class KmpObservable<T: AnyObject>: ObservableObject {
    let kmp: T
    private let onDeinit: () -> Void

    init(_ kmp: T, onDeinit: @escaping () -> Void) {
        self.kmp = kmp
        self.onDeinit = onDeinit
    }

    deinit {
        onDeinit()
    }
}

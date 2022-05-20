import SwiftUI
import shared

@main
struct iOSApp: App {
    init() {
        KoinInit().start()
    }
	var body: some Scene {
		WindowGroup {
            ContentViewWithViewModelFromShared()
		}
	}
}

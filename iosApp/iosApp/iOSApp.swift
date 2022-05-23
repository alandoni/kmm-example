import SwiftUI
import shared

@main
struct iOSApp: App {
    init() {
        KoinInit.companion.start()
    }
	var body: some Scene {
		WindowGroup {
            ContentViewWithViewModelFromShared()
            //ContentView()
		}
	}
}

import SwiftUI

@main
struct iOSApp: App {
    init() {
        NapierKt.initNapier()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
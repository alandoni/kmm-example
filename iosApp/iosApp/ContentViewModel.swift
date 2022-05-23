import Foundation
import shared

@MainActor
class ContentViewModel: ObservableObject {
    
    @Published var state: ViewModelState = .loading
    @Published var text: String = ""
    let repository = LaunchesRepository()
    
    func getRocketLaunches() {
        Task.init {
            state = .loading
            do {
                state = .success(value: try await repository.getLaunches())
            } catch {
                state = .error(error: error)
            }
        }
    }
    
    
    enum ViewModelState {
        case loading
        case error(error: Error)
        case success(value: [ModelsRocketLaunch])
    }
}

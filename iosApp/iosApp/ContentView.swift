import SwiftUI
import Foundation
import shared

struct ContentViewWithViewModelFromShared: View {
    let viewModel = MainViewModel()

    @State var state: MainViewModelState? = MainViewModelState.Loading()

    func load() {
        viewModel.state.watch { newState in
            self.state = newState
        }
        viewModel.getRocketLaunches()
    }
    
	var body: some View {
        if state is MainViewModelState.Loading {
            ProgressView()
                .onAppear(perform: {
                    self.load()
                })
        } else if state is MainViewModelState.Error {
            let error = (state as? MainViewModelState.Error)?.error.description() ?? "Error!"
            Text(error)
        } else {
            let rocketLaunches = (state as? MainViewModelState.Success)?.value ?? []
            if rocketLaunches.count == 0 {
                Text("Empty List")
            } else {
                List(rocketLaunches, id: \.self) { launch in
                    RocketLaunchesRow(launch: launch)
                }
            }
        }
    }
}

struct ContentView: View {
    @ObservedObject var viewModel = ContentViewModel()

    func load() {
        viewModel.getRocketLaunches()
    }
    
    var body: some View {
        switch viewModel.state {
        case .loading:
            ProgressView()
                .onAppear(perform: {
                    self.load()
                })
        case .error(let err):
            let error = err.localizedDescription
            Text(error)
        case .success(let value):
            let rocketLaunches = value
            if rocketLaunches.count == 0 {
                Text("Empty List")
            } else {
                List(rocketLaunches, id: \.self) { launch in
                    RocketLaunchesRow(launch: launch)
                }
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}

struct RocketLaunchesRow: View {
    var launch: ModelsRocketLaunch

    var body: some View {
        Text(self.launch.missionName)
    }
}

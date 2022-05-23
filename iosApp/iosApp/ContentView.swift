import SwiftUI
import Foundation
import shared

struct ContentViewWithViewModelFromShared: View {
    let viewModel = MainViewModel()

    @State var state: MainViewModelState? = MainViewModelState.Loading()
    @State var text: String = ""

    func load() {
        viewModel.state.collect { newState in
            self.state = newState
        }
        viewModel.text.collect { text in
            self.text = (text as? String) ?? ""
        }

        viewModel.getRocketLaunches()
    }
    
	var body: some View {
        TextField("", text: $text)
            .background(Color.green)
            .onChange(of: text) { newValue in
                viewModel.text.tryEmit(value: newValue as NSString)
            }
        Text("\(text.count)")
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

@MainActor
struct ContentView: View {
    @ObservedObject var viewModel = ContentViewModel()

    func load() {
        viewModel.getRocketLaunches()
    }
    
    var body: some View {
        TextField("", text: $viewModel.text)
            .background(Color.green)
            .onChange(of: viewModel.text) { newValue in
                //viewModel.text.tryEmit(value: newValue as NSString)
            }
        Text("\(viewModel.text.count)")
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

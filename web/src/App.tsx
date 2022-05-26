import React, { useState, useEffect } from 'react';
import logo from './logo.svg';
import './App.css';
import KmmExample from 'kmmexample-shared';

const MainViewModel = KmmExample.com.example.kmmexample.viewmodel.MainViewModel;
const MainViewModelState = KmmExample.com.example.kmmexample.viewmodel.MainViewModelState;
const RocketLaunch = KmmExample.com.example.kmmexample.data.models.RocketLaunch;
const LaunchesRepository = KmmExample.com.example.kmmexample.data.repository.LaunchesRepository;

class viewModel {
  const repository = LaunchesRepository()

  async function getRocketLaunches() {
      const launches = await repository.getLaunches()
  }
}

function App() {
  const [state, setState] = useState(MainViewModelState.Loading);

  useEffect(() => {
    const viewModel = new MainViewModel();
    
    viewModel.state.collect((newState: React.SetStateAction<KmmExample.com.example.kmmexample.viewmodel.MainViewModelState>) => {
      setState(newState);
    });
    
    //viewModel.getRocketLaunches();
  });

  if (state.constructor.name === 'Loading') { 
    return <h1>In Progress</h1>;
  } else if (state instanceof MainViewModelState.Error) {
    return <div>{state.error.toString()}</div>
  }

  const launches = (state as any).value

  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header>
      <section>
      {launches.map((rocketLaunch: any) => { 
        return <p>{rocketLaunch.missionName}</p> 
      })}
      </section>
    </div>
  );
}

export default App;

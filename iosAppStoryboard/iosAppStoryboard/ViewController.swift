//
//  ViewController.swift
//  iosAppStoryboard
//
//  Created by Alan Donizete Quintiliano on 20/05/22.
//

import UIKit
import shared

class ViewController: UIViewController {
    @IBOutlet weak var tableView: UITableView!
    @IBOutlet weak var errorLabel: UITextField!
    @IBOutlet weak var label: UILabel!
    @IBOutlet weak var progressView: UIActivityIndicatorView!
    
    let viewModel = MainViewModel()
    var rocketLaunches: [ModelsRocketLaunch] = []
    
    override func viewDidLoad() {
        super.viewDidLoad()
        tableView.register(
            UITableViewCell.self,
            forCellReuseIdentifier: "Cell"
        )
        tableView.delegate = self
        tableView.dataSource = self
        
        self.errorLabel.bindText(flow: viewModel.text)
        
        let commonFlow = viewModel.text.map { t in
            return "\((t as? String)?.count ?? 0)"
        }
        if let flow = commonFlow as? CommonFlow<NSString> {
            self.label.bindText(flow: flow)
        }
        
        viewModel.state.collect { state in
            if state is MainViewModelState.Loading {
                self.tableView.isHidden = true
                self.progressView.isHidden = false
            } else if state is MainViewModelState.Error {
                let error = (state as? MainViewModelState.Error)?.error.message ?? "Error"
                self.errorLabel.text = error
                self.tableView.isHidden = true
                self.progressView.isHidden = true
            } else {
                self.rocketLaunches = (state as? MainViewModelState.Success)?.value ?? []
                self.tableView.isHidden = false
                self.tableView.reloadData()
                self.progressView.isHidden = true
            }
        }
        viewModel.getRocketLaunches()
    }
}

extension ViewController: UITableViewDelegate {
    
}

extension ViewController: UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.rocketLaunches.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "Cell") ?? UITableViewCell()
        cell.textLabel?.text = self.rocketLaunches[indexPath.row].missionName
        return cell
    }
}

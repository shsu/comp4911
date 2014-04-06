<content>
  <h1 class="secondary">Assign Employee To Project</h1><br />     
  <div ng-controller="AEPController">
    <div class="row">
      <ul class="list-inline">
        <li class="col-xs-3">User ID: {{cUser.id}}</li>
        <li class="col-xs-3">User Name: {{cUser.firstName}} {{cUser.lastName}}</li>
      </ul>
    </div>
    <div class="row">
      <p>Employee</p>
      <p class="col-xs-12 no-pl"><input type="text" ng-model="query.projectNumber" class="col-xs-5" placeholder="Enter Project Number"/></p>
      <div class="col-xs-5 results">
        <table class="table table-hover">
          <tbody>
            <tr>
              <th style="width: 33%;">Project Number</th>
              <th style="width: 33%;">Project Name</th>
            </tr>
            <tr ng-click="selectP($index)" class="{{project.selected}} disabled-{{project.disabled}}" ng-repeat="project in projects | filter:query | limitTo: quantity">
              <td>{{project.projectNumber}}</td> 
              <td>{{project.projectName}}</td>
            </tr>
          </tbody>
        </table>    
      </div>
      <div class="col-xs-2">
        <button ng-click="addP()" class="btn btn-primary btn-block">Add</button>
        <button ng-click="removeP()" class="btn btn-primary btn-block">Remove</button>
      </div>
      <div class="col-xs-5 results">
        <table class="table table-hover">
          <tbody>
            <tr>
              <th style="width: 33%;">Project Number</th>
              <th style="width: 33%;">Project Name</th>
            </tr>
            <tr ng-click="addedSelectP($index)" class="{{selectedProject.selected}}" ng-hide="!selectedProjects" ng-repeat="selectedProject in selectedProjects">
              <td>{{selectedProject.projectNumber}}</td> 
              <td>{{selectedProject.projectName}}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
    <div class="row">
      <button class="col-lg-2 btn btn-primary pull-right">Save</button>
    </div>
  </div>
</content>
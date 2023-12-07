def call(Map config=[:]){
node {
	stage('SCM') {
		echo 'Gathering code from version control'
		git branch: '${branch}', url: 'https://github.com/ebg1g18/JenkinsGroovy.git'
	}
    stage('Build') {
		try{
			echo 'Building....'
			withDotNet(sdk: "sdk6"){
				sh 'dotnet build ' + config.target
				echo 'Building new feature'
				releasenotes(changes: "true");
			}
		}catch(ex){
			echo 'Something went wrong'
			echo ex.toString()
			currentBuild.result = 'FAILURE'
		}
		finally{
			// cleanup
		}
    }
    stage('Test') {
        echo 'Testing....'
    }
    stage('Deploy') {
        echo 'Deploying....'
    }
}
}

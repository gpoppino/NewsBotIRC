pipeline {
   agent any

   tools {
      // Install the Maven version configured as "M3" and add it to the path.
      maven "M3"
   }

   stages {
      stage('Build') {
         steps {
            checkout scm

            // Run Maven on a Unix agent.
            sh "mvn -Dmaven.test.failure.ignore=true clean package"
         }

      }

      stage('SCA from Fortify') {
          steps {
            fortifyUpdate updateServerURL: 'https://update.fortify.com'

            fortifyClean addJVMOptions: '', buildID: 'NewsBotIRC', logFile: '', maxHeap: ''

            fortifyTranslate addJVMOptions: '', buildID: 'NewsBotIRC', excludeList: '', logFile: '', maxHeap: '', projectScanType: fortifyMaven3(mavenOptions: '')

            fortifyScan addJVMOptions: '', addOptions: '', buildID: 'NewsBotIRC', customRulepacks: '', logFile: '', maxHeap: '', resultsFile: 'NewsBotIRC.fpr'

            fortifyUpload appName: 'NewsBotIRC', appVersion: '0.2.1-SNAPSHOT', failureCriteria: '', filterSet: '', pollingInterval: '', resultsFile: 'NewsBotIRC.fpr'
            }
          post {
            // If Maven was able to run the tests, even if some of the test
            // failed, record the test results and archive the jar file.
            success {
                junit '**/target/surefire-reports/TEST-*.xml'
                archiveArtifacts 'target/*.jar'
            }
        }
      }
   }
}

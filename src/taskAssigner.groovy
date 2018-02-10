def project = 'task-assigner'
def branchApi = new URL("https://api.github.com/repos/kaismi/${project}/branches")
def branches = new groovy.json.JsonSlurper().parse(branchApi.newReader())
branches.each {
    def branchName = it.name
    def jobName = "${project}-${branchName}".replaceAll('/','-')
    job(jobName) {
        scm {
            git("git://github.com/kaismi/${project}.git", branchName)
        }
        steps {
            maven("clean install")
        }
    }
}

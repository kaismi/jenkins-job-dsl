package utils

class JobUtils {

    static
    def createBuildJob(project, dslFactory, branchApiPrefix = "https://api.github.com/repos/kaismi/", gitUrlPrefix = "git://github.com/kaismi/") {
        def branchApi = new URL(branchApiPrefix + "${project}/branches")
        def branches = new groovy.json.JsonSlurper().parse(branchApi.newReader())
        branches.each {
            def branchName = it.name
            def jobName = "${project}-${branchName}".replaceAll('/', '-')
            dslFactory.job(jobName) {
                scm {
                    git(gitUrlPrefix + "${project}.git", branchName)
                }
                steps {
                    maven("clean package")
                }
            }
        }
    }
}

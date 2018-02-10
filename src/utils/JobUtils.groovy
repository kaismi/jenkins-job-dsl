package utils

import groovy.json.JsonSlurper

class JobUtils {

    static def createBuildJobs(project, dslFactory, branchApiPrefix = "https://api.github.com/repos/kaismi/", gitUrlPrefix = "git://github.com/kaismi/", mavenCall = "clean package") {
        def branchApi = new URL(branchApiPrefix + "${project}/branches")
        def branches = new JsonSlurper().parse(branchApi.newReader())
        branches.each {
            def branchName = it.name
            def jobName = "${project}-${branchName}".replaceAll('/', '-')
            dslFactory.job(jobName) {
                scm {
                    git(gitUrlPrefix + "${project}.git", branchName)
                }
                steps {
                    maven(mavenCall)
                }
            }
        }
    }
}

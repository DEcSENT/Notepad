# Make it more obvious that a PR is a work in progress and shouldn't be merged yet
warn("PR is classed as Work in Progress") if github.pr_title.include? "[WIP]"

# Warn when there is a big PR
warn("Big PR") if git.lines_of_code > 300

# Mainly to encourage writing up some reasoning about the PR, rather than
# just leaving a title
if github.pr_body.length < 5
    warn("Please provide a summary in the Pull Request description")
end

###
# Android Lint
###
android_lint.gradle_task = 'app:lint'
android_lint.report_file = 'app/build/reports/lint-results.xml'
android_lint.lint(inline_mode: true)
# With this flag Danger will check only new files
# android_lint.filtering = true

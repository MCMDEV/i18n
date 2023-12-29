rootProject.name = "i18n"

includePrefixed("adventure")
includePrefixed("api")
includePrefixed("common")
includePrefixed("paper")
includePrefixed("velocity")

fun includePrefixed(name: String) {
    val projectPath = "${rootProject.name}-${name.replace(':', '-')}"
    val projectDir = file(name.replace(':', '/'))

    include(projectPath)
    project(":$projectPath").projectDir = projectDir
}
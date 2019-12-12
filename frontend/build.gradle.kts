import org.jetbrains.kotlin.gradle.frontend.webpack.WebPackExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

plugins {
    id("kotlin2js")
    id("org.jetbrains.kotlin.frontend")
    base
}

repositories {
    maven("https://dl.bintray.com/kotlin/kotlin-js-wrappers")
    jcenter()
}

dependencies {
    "implementation"("org.jetbrains.kotlin", "kotlin-stdlib-js", "1.3.50")
    "compileOnly"("org.jetbrains.kotlinx", "kotlinx-html-js", "0.6.12")
    "compileOnly"("org.jetbrains", "kotlin-react", "16.9.0-pre.85-kotlin-1.3.50")
    "compileOnly"("org.jetbrains", "kotlin-react-dom", "16.9.0-pre.85-kotlin-1.3.50")
}

sourceSets.main {
    withConvention(KotlinSourceSet::class) {
        kotlin.srcDir("src")
    }
}

tasks.compileKotlin2Js {
    kotlinOptions {
        outputFile = "${buildDir}/js/index.js"
        moduleKind = "commonjs"
        sourceMap = true
        metaInfo = true
        main = "call"
    }
}

kotlinFrontend {

    downloadNodeJsVersion = "12.13.0"

    npm {
        dependency("react", "16.10.2")
        dependency("react-dom", "16.10.2")
        dependency("core-js", "3.3.2")
        dependency("bootstrap", "4.1.3")
        dependency("jquery", "3.3.1")
        dependency("popper.js", "1.14.7")
        devDependency("css-loader")
        devDependency("style-loader")
    }

    bundle<WebPackExtension>("webpack") {
        this as WebPackExtension
        bundleName = "main"
        mode = "development"
        sourceMapEnabled = true
        contentPath = file("$buildDir/public")
        publicPath = "/"
        host = "localhost"
        port = 3000
        proxyUrl = "http://localhost:8080"
    }
}

tasks.register<Copy>("public") {
    dependsOn("bundle")
    from("$buildDir/bundle/main.bundle.js", "$projectDir/public/")
    into("$buildDir/public")
}

tasks.register<Zip>("distr") {
    dependsOn("public")
    from("$buildDir/public/")
}

tasks.build {
    dependsOn("distr")
}

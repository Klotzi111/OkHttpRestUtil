# OkHttpRestUtil
Helper library for REST APIs using OkHttp and with strong integration with Gson.

Just another utils library for OkHttp. This project specialises for REST APIs and has strong integration with Gson. 

## Usage
You can get it via jitpack or compile it yourself.

### Jitpack
Add the following in your build.gradle:

```groovy
repositories {
	maven {
		url "https://jitpack.io"
	}
}

dependencies {
	implementation 'com.github.Klotzi111:OkHttpRestUtil:main-SNAPSHOT'
}
```

### Compile self
Do the following to use this library:
 - Download, build and publish this library to your local maven repository (use the gradle task `publishToMavenLocal` for that)
 - Add the following in your build.gradle:
 
```groovy
repositories {
	mavenLocal()
}

dependencies {
	implementation 'de.klotzi111.util:OkHttpRestUtil:1+'
}
```

### Code Usage
**TODO**
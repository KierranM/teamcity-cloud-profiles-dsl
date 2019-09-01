# TeamCity Cloud Profiles DSL
Kotlin DSL library for TeamCity Cloud Agent profiles

The library provides a number of extensions to aid in creating TeamCity Cloud Agent Profiles.

## Usage

1. Add jitpack repository to your .teamcity/pom.xml  

```xml
    <repositories>
      <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
      </repository>
    </repositories>
```


2. Add the library as a dependency in .teamcity/pom.xml

```xml
   <dependency>
	 <groupId>com.github.kierranm</groupId>
	 <artifactId>teamcity-cloud-profiles-dsl</artifactId>
	 <version>1.0.0</version>
   </dependency>
```

[![](https://jitpack.io/v/KierranM/teamcity-cloud-profiles-dsl.svg)](https://jitpack.io/#KierranM/teamcity-cloud-profiles-dsl)

## Examples

### EC2 Cloud Profile
```kotlin
import io.github.kierranm.teamcity.v2018_2.projectFeatures.ec2CloudProfile


project {
    features {
        ec2CloudProfile {
            name = "my-profile"
            id = "my-profile"
            terminateAfterBuild = true
            terminateAfterIdleTime = 60
            maxRunningInstances = 5
            useInstanceIAMRole = true
            region = "ap-southeast-2"
            
            images {
                ami {
                    ami = "ami-124135125"
                    ec2KeyPair = "MyKeyPair"
                    subnetIds = listOf(
                        "subnet-14912094",
                        "subnet-12413251"
                    )
                    instanceType = "t3.small"
                    userData = """
                        #!/bin/bash -e
                        echo "Hello"
                    """.trimIndent()
                    iamInstanceProfile = "MyProfile"
                    instanceTags = mapOf(
                        "Name" to "MyAgent"
                    )
                    agentNamePrefix = "my-ec2-agent"
                    useSpotInstance = true
                    spotInstancePrice = 0.022
                    securityGroupIds = listOf(
                        "sg-12412512"
                    )
                }
            }
        }
    }
}
```
# TeamCity Cloud Profiles DSL
Kotlin DSL library for TeamCity Cloud Agent profiles

The library provides a number of extensions to aid in creating TeamCity Cloud Agent Profiles.

[ ![Download](https://api.bintray.com/packages/kierranm/io.github.kierranm/teamcity-cloud-profiles-dsl/images/download.svg) ](https://bintray.com/kierranm/io.github.kierranm/teamcity-cloud-profiles-dsl/_latestVersion)

## Usage

1. Add this Bintray repository to your .teamcity/pom.xml  

```xml
    <repositories>
      <repository>
        <id>bintray-kierranm</id>
        <url>https://dl.bintray.com/kierranm/io.github.kierranm</url>
      </repository>
    </repositories>
```


2. Add the library as a dependency in .teamcity/pom.xml

```xml
   <dependency>
	 <groupId>io.github.kierranm</groupId>
	 <artifactId>teamcity-cloud-profiles-dsl</artifactId>
	 <version>0.1.2</version>
   </dependency>
```

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
                    amiId = "ami-124135125"
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
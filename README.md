# ArchiDuchess


[![license](https://img.shields.io/github/license/myamoto/ArchiDuchess.svg)](LICENSE)
[![standard-readme compliant](https://img.shields.io/badge/readme%20style-standard-brightgreen.svg?style=flat-square)](https://github.com/myamoto/ArchiDuchess)

JAVA library to transform well-formed [Archi](https://www.archimatetool.com/download) documents into [MxGraph](https://jgraph.github.io/mxgraph/) schemas.\
At the time or writing (23/03/2021) it is compatible with Archi 4.8.1 (January 18 2021) and mxGraph 4.2.2 (October 28 2020).

## Sample

Most basic example :\
\
![alt text](https://raw.githubusercontent.com/myamoto/ArchiDuchess/master/src/readme/archi_hello_the_world.png)\
\
will results in :\
\
![alt text](https://raw.githubusercontent.com/myamoto/ArchiDuchess/master/src/readme/hello_the_world.png)
\
Some elements have no special representation. Colors are mostly respected :\
Palette overview :\
![alt text](https://raw.githubusercontent.com/myamoto/ArchiDuchess/master/src/readme/all.png)

## Install (maven)
include this snippet in your pom's dependencies.

```xml
<dependency>
	<groupId>org.toolup.archi</groupId>
	<artifactId>archiduchess</artifactId>
	<version>x.y.z</version>
</dependency>
```
## Usage

```java
/*
* configure gitlab.
* note : not tested against github, but could easily be adapted.
*/
ArchiGraphService s = new ArchiGraphService();
s.config("https://my-gitlab-repo.org"
		, "{{my_git_personnal_token}}"
		, "https://my-gitlab-repo.org/path/to/MyArchiProject"
		, "/archiduchess/working/directory");

/* pull project and points to master's last commit.
* parse directory directory structure and deduce "groups".
* one group = one directory with one or more ".archimate" file.
*/
Gallery galry = s.readGalery(); 

for (String grpPath : galry.getGroups()) { //iterate over groups
	Group grp = s.readGroup(grpPath);	//parse group graphs. one "group" correspond to a directory of "Archi" files.
	for(Graph graph : grp.getGraphs()) {//each graph correspond to one "Archi" file.
		for(String view : graph.getViews()) { //One "Archi" file can contain several schemas.
			s.readMxGraph(grp.getPath(), graph.getFileName(), view);//convert to mxGraph
		}
	}
}			
```
## known issues

### Why do I get extra-space at the top of the schema ?
Make sure you start your schema at (0, 0) - in the top left hand corner - in Archi's canvas.
Otherwise, display will not be optimal.

### only part of the elements are decorated, why ?
Simply because they have not be drawn yet. I specifically customized the elements I use all the time.
Others will be displayed as simple rectangles.
Fill in a feature request if you need some elements to be decorated.

### parsing error : "Unsupported xsi:type "?
As stated above version 1.0.0 is capable of parsing Archi up to version 4.8.1.\
It was also tested on an old version of Archi from 2017. If you're using a version superior to 4.8.1, you may hit parsing issues ("Unsupported xsi:type"). If that's the case, fill-in a bug report.\
A simple work-around in that case would be to avoid using those new elements and wait for the correction.

## Contributing

PRs accepted.

## License

the code is available under the [Apache License](LICENSE).

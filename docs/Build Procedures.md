### Download OpenGrid Service Template code
<pre>
git clone https://github.com/Chicago/opengrid-svc-template.git
</pre>

### Install Dependencies
Install [maven](https://maven.apache.org/install.html).

### Customize/Configure
Edit Mongo settings in `./opengridservice/src/main/resources/config/application.properties`. *Add instructions later to initialize test Mongo database*

### Run Maven Tasks
<pre>
cd opengrid-svc-template/opengridservice
mvn clean
mvn test
mvn package
</pre>
### Deploy
Deploy packaged war file in *target* folder into your application server.

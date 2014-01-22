apt-get -y update
apt-get install -y software-properties-common python-software-properties
add-apt-repository -y ppa:webupd8team/java
apt-get -y update
/bin/echo debconf shared/accepted-oracle-license-v1-1 select true | /usr/bin/debconf-set-selections
apt-get -y install oracle-java7-installer oracle-java7-set-default

echo "export JAVA_HOME=/usr/lib/jvm/java-7-oracle/jre" >> ~/.bashrc
apt-get -y install htop maven

cd /vagrant
#mvn jboss-as:start

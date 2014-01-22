# COMP 4911 Dev Environment Setup

## Part I: Host Environment

1. Oracle Java 7
2. Eclipse + ADT
3. Git
4. Maven

-- See [Jung's docs](https://drive.google.com/#folders/0BwUn12Wzvvu8cG0yQ0lncm1ESU0) for more info.

## Part II: Virtual Environment

1. [Download VirtualBox](https://www.virtualbox.org/wiki/Downloads)
2. [Download Vagrant](http://www.vagrantup.com/downloads.html)
3. Clone the [comp4911 project code](https://github.com/shsu/comp4911) if you haven't done so.
4. CD To that directory and execute the following command to start the VM:
  
        vagrant up

For Mac Users with Homebrew Cask:

    brew cask install virtualbox
    brew cask install vagrant
  
## Vagrant Quickstart Guide

To Start the VM

    vagrant up

To SSH into the VM
  
    vagrant ssh
  
To Shutdown the VM
  
    vagrant halt
 

telnet-chat
===========

Pre install requirements:

1) Install redis:
  brew install redis

2) Run redis server:
  redis-server

How to run it:

1) Install gvm:
  curl -s get.gvmtool.net | bash
  And run:
  source "/home/ubuntu/.gvm/bin/gvm-init.sh"

2) Install groovy:
  gvm install groovy 2.1.6
  (Mark as default version)

3) Clone this project:
  git clone https://github.com/mwaisgold/telnet-chat.git

4) Go into src folder:
  cd telnet-chat/src/

5) Run the server:
  groovy base.groovy





import os
import commands

import api
import winOnLinux

def preEnv():

	# if os.path.exists("tmpFolder"):
	# 	print "can xoa thu muc 'tmpFolder' truoc khi thuc hien"
	# 	return 1
	api.retCmd("rm -r  tmpFolder")
	api.retCmd("mkdir -p tmpFolder/reg")
	api.retCmd("mkdir -p tmpFolder/winLog")
	api.retCmd("mkdir -p tmpFolder/browserCache")
	api.retCmd("mkdir -p tmpFolder/other")
	return 0

def startCheck(path):
	if preEnv():
		exit(0)
	pathLs=api.retCmd("ls "+path)
	if "ProgramData" in pathLs or "Program Files" in pathLs:
		print "windows check"
		winOnLinux.start(path)

	elif "root" in pathLs or "mnt" in pathLs:
		print "linux dang hoan thien"

startCheck("/media/uss/944A90314A90125A")

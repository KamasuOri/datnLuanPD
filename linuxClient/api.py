import os
import commands

toolDir = "tool"

def retCmd(cmd):
	return commands.getstatusoutput(cmd)[1]

def checkPath(path):
	if " " in path:
		return "'"+path+"'"
	else:
		return path

def copyFile(dest,src):
	if  os.path.isfile(dest):

		retCmd("cp "+checkPath(dest)+" "+checkPath(src))
	elif os.path.isdir(dest):
		if  os.path.isfile(dest):
			return 1

		retCmd("cp -r "+checkPath(dest)+" "+checkPath(src))
	else:
		return 1
	return 0


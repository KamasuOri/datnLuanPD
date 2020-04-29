# -*- coding: utf-8 -*- 
import os
import commands
import  time
import sys
import api
import winOnLinux

outputDir=''
path=''


def preEnv():
	""" Tạo cây thư mục tạm để lưu kết quả đầu ra
	"""
	global outputDir

	if os.path.exists(api.fillPath(outputDir+"tmpFolder")):
		today = str(time.time()).replace(".","")
		api.retCmd("mv "+api.fillPath(outputDir+"tmpFolder")+" "+api.fillPath(outputDir+"tmpFolder"+today))
	api.retCmd("mkdir -p "+api.fillPath(outputDir+"tmpFolder/reg"))
	api.retCmd("mkdir -p "+api.fillPath(outputDir+"tmpFolder/winLog"))
	api.retCmd("mkdir -p "+api.fillPath(outputDir+"tmpFolder/browserCache"))
	api.retCmd("mkdir -p "+api.fillPath(outputDir+"tmpFolder/other"))
	api.retCmd("mkdir -p "+api.fillPath(outputDir+"tmpFolder/network"))
	api.retCmd("mkdir -p "+api.fillPath(outputDir+"tmpFolder/fileCopyOption"))
	api.retCmd("mkdir -p "+api.fillPath(outputDir+"tmpFolder/FileHaveBeenOpen"))
	return 0

def main():
	"""
	Đọc file control.txt (được tạo ra trong code java) để lấy đường dẫn của ổ đĩa cần lấy chứng cứ và nơi lưu chứng cứ khi thu thập xong
	"""
	global path
	global outputDir
	f = open("control.txt","r").read().split("\n")

	outputDir=f[0]
	path=f[1]
	if outputDir[-1] != "/":
		outputDir=outputDir+"/"
	if path[-1] != "/":
		path=path+"/"

	if "preEnv" in sys.argv[1]:
		preEnv()
	pathLs=api.retCmd("ls "+path)
	if "ProgramData" in pathLs or "Program Files" in pathLs:
		winOnLinux.main(path,outputDir,sys.argv[1])

	return 0


if __name__ == '__main__':
    main()

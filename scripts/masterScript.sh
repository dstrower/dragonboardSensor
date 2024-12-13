if test -f /home/linaro/shutdown.txt; then
   rm /home/linaro/shutdown.txt 
fi
if test -f /home/linaro/sawFile.txt; then
   rm /home/linaro/sawFile.txt 
fi
while true ;do
	#echo "In loop"
	sleep 10
	if test -f /home/linaro/shutdown.txt; then
		touch /home/linaro/sawFile.txt
		#echo "Found file"
		sudo poweroff &
	fi
done

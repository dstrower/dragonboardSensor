if test -f /home/linaro/shutdown.txt; then
   rm /home/linaro/shutdown.txt 
fi
while true ;do
	echo "In loop"
	sleep 10
	if test -f /home/linaro/shutdown.txt; then
		echo "Found file"
		poweroff
	fi
done

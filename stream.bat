C:\gstreamer\1.0\x86_64\bin\gst-launch-1.0.exe -v udpsrc port=5808 caps = "application/x-rtp, media=(string)video, clock-rate=(int)90000, encoding-name=(string)H264, payload=(int)96" ! rtph264depay ! decodebin ! videoconvert ! autovideosink
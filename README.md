# MiningMonitor

- Monitor your mining rigs running in a local network or over internet.
- It is better to have api read only, if you are accessing over internet.
- monitor.conf file should contain all the rigs needed to be monitored.
- If any of the rigs being monitored goes offline, the wav file configured for that rig in monitor.conf will be played.
- I have added functionality for LOL_MINER alone, you can add for others in miners/Miner.java

![Screenshot (1)](https://user-images.githubusercontent.com/46281355/157230801-fa68baa3-5d2f-4ea6-9739-bca23f98cdf7.png)

# monitor.conf

```
[
    {
        "name":     "VLAD",
        "miner":    "LOL_MINER",
        "ip":       "192.168.0.129",
        "port":     "44444",
        "notificationWav":  "C:\\Users\\phani\\Downloads\\unravel.wav"
    },
    {
        "name":     "HITLER",
        "miner":    "LOL_MINER",
        "ip":       "192.168.0.192",
        "port":     "44444",
        "notificationWav":  "C:\\Users\\phani\\Downloads\\unravel.wav"
    }
]
```

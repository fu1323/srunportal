hs.task.new("/usr/sbin/scutil", nil, {
    "--nc",
    "stop",
    "Shadowrocket"
}):start()
 -- 关闭多余vpn,准备portal认证

if hs.location.servicesEnabled() then
    hs.location.start()
    print("Location triggered:", hs.location.get())
end
local wifiWatcher = nil

function wifiChangedCallback(watcher, message, interface)
    local currentWifi = hs.wifi.currentNetwork()
    -- 替换为你要自动认证的 Wi-Fi 名称
    if currentWifi == "xxx" then
        -- 运行你的认证脚本
        hs.task.new("xxx.sh", nil):start()
--         .sh里用java-jar 运行此app
    end
end

wifiWatcher = hs.wifi.watcher.new(wifiChangedCallback)
wifiWatcher:start()

-- 关闭mac默认portal自动弹窗
--[[  sudo defaults write /Library/Preferences/SystemConfiguration/com.apple.captive.control Active -bool false]]

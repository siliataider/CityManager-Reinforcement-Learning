const socketEvents = {
    connect : 'connect',
    new_building:"build",
    run_simulation:"start",
    refresh_agents:"updateAgent",
    change_speed: "speed",
    change_weather: "weather",
    stop_simulation:"stop",
    save_agent: "saveAgent",
    load_agent: "loadAgent"
}

export default socketEvents;
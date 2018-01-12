var config = {
    port: 8080,
    ipaddress: "0.0.0.0",
    hotheadPath: "/pages/hothead/",
    hotheadUri: "/hothead",
    restricted: ["-server", "-config", "/rest", "db.js", "/node_modules"],
};

module.exports = config;
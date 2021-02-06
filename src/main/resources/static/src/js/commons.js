function getContext() {
    var curPath = window.document.location.href;
    var pathName = window.document.location.pathname;
    var index = curPath.indexOf(pathName);
    var localhost = curPath.substring(0, index);
    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
    return (localhost + projectName);
}

function getParam(key) {
    var query = window.location.search.substring(1);
    var params = query.split("&");
    for (var i = 0; i < params.length; i++) {
        var kv = params[i].split("=");
        if (kv[0] == key) {
            return kv[1];
        }
    }
    return false;
}
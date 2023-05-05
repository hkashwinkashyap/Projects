import axios from 'axios'

axios.defaults.baseURL = 'http://localhost:8080'

export default {
    getRouteList() {
        return ajax("/route", "get");
    },
    getStopList() {
        return ajax("/stop", "get");
    },
    addStop(stopName, routeName, beforeStopName) {
        return ajax("/route", "post", {
            data: {
                stopName: stopName,
                routeName: routeName,
                beforeStopName: beforeStopName
            }
        })
    },
    allRoutesAtStop(stopName) {
        return ajax("/route/" + stopName, "get")
    },
    allRoutesAtStopAndTime(stopName, time) {
        return ajax("/route/" + stopName + "/" + time, "get")
    },
    allScheduleInStop(stopName) {
        return ajax("/time/" + stopName, "get")
    }
}

/**
 * An ajax method for sending the Asynchronous requests to server
 * @param url
 * @param method get|post|put|delete..
 * @param options such options when sending the request to server.
 *                params, like queryString. if an url is index?a=1&b=2, params = {a: '1', b: '2'}
 *                data post data, use for method put|post
 * @returns a prototype of axios.
 */
function ajax(url, method, options) {
    if (options !== undefined) {
        var {params = {}, data = {}} = options
    } else {
        params = data = {}
    }
    return axios({
        url,
        method,
        params,
        data
    });
}

import axios from "axios";
const SERVER_URL = process.env.SERVER_URL || 'http://172.17.0.200:9000';

export default axios.create({
    baseURL: SERVER_URL,
    headers: {
        'Access-Control-Allow-Origin': '*',
        "Content-type": "application/json",
    }
});

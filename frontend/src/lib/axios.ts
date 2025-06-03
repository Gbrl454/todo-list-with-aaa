import axios from "axios";

export const api = axios.create({
  baseURL: 'http://backend:8080/todo-list',
  withCredentials: true
});



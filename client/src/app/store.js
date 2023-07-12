import { configureStore } from "@reduxjs/toolkit";
import UserSliceReducer from "./reducer/User.reducer";

export const store = configureStore({
  reducer: {
    user: UserSliceReducer,
  },
});

export const dispatch = store.dispatch;
export const getState = store.getState;

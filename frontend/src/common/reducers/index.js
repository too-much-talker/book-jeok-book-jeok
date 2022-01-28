import { combineReducers } from "redux";
import authReducer from "./modules/auth";
import { persistReducer } from "redux-persist";
import storage from "redux-persist/lib/storage";

const persistConfig = {
  key: "root",
  storage: storage,
};

const rootReducer = combineReducers({
  authReducer,
});

export default persistReducer(persistConfig, rootReducer);

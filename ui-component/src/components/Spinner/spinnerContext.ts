import { createContext } from "react";

// Create a new context for managing spinner state across components
// This context will be used to show/hide a loading spinner in the app
// The initial value is an empty object, but it can be updated with actual spinner state later
export const spinnerContext = createContext<any>({});

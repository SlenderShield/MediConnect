import React, { useState } from "react";
import { spinnerContext } from "./spinnerContext";

// SpinnerProvider component to manage spinner visibility
const SpinnerProvider = ({ children }) => {
  const [showSpinner, setShowSpinner] = useState<any>(false);

  return (
    <spinnerContext.Provider value={{ setShowSpinner }}>
      {/* Show spinner modal if showSpinner is true */}
      {showSpinner && (
        <div
          className="modal show d-block"
          style={{ backgroundColor: "rgba(0,0,0,0.5)" }}
        >
          <div className="modal-dialog modal-dialog-centered d-flex justify-content-center">
            {/* Spinner icon */}
            <div className="spinner-border text-danger" role="status"></div>
          </div>
        </div>
      )}
      {/* Render child components */}
      {children}
    </spinnerContext.Provider>
  );
};

export default SpinnerProvider;

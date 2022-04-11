-- Create a table named employees
CREATE TABLE "employees" (
    "id" INTEGER NOT NULL,
    "first_name" TEXT NOT NULL,
    "last_name" TEXT NOT NULL,
    "email" TEXT,
    PRIMARY KEY("id")
);

-- Extra practice: Create a table named employee_relationships
CREATE TABLE "employee_relationships" (
    "manager_id" INTEGER NOT NULL,
    "employee_id" TEXT NOT NULL,
    PRIMARY KEY("manager_id, employee_id"), -- Set primary key as combination of two columns
    FOREIGN KEY("manager_id") REFERENCES "employees"("id"), -- Specify manager_id as referencing employees' ID column
    FOREIGN KEY("employee_id") REFERENCES "employees"("id") -- Specify employee_id as referencing employees' ID column
);
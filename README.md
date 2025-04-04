# Distributed Share Management System (CORBA)

## Done by: Ahish Mahesh  
COMP 6231 – DSD Assignment 2  

## Software Architecture  
The solution consists of three servers (NYK, TOK, and LON), and CORBA is used by client applications to communicate with the servers. The servers communicate amongst each other through UDP.  

- Clients communicate with their respective servers to buy and sell shares.  
- If clients need to buy shares from other locations, this operation is performed through their own server.  
- The servers use UDP to communicate amongst themselves for buying, selling, and swapping shares for clients.  

## Users of the System  
- **Admins**: Can add, remove, and list share availability in their respective servers. They can also buy, sell, swap, and display their purchased shares.  
- **Buyers**: Can only buy, sell, swap, and display their purchased shares.  

## Data Structures Used  
- **Concurrent HashMap**: Used to store share information and share purchase history for buyers within each server.  
  - The **shares HashMap** is a two-layer structure:  
    ```java
    ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>>
    ```
  - The **user shares HashMap** is a three-layer structure:  
    ```java
    ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>>>
    ```
    This stores the purchase history of each buyer, including those from other regions. E.g., if a NYK buyer purchases shares from the TOK server, TOK keeps track of that purchase.  
- **CopyOnWriteArrayList**: Used to store server names, addresses, and ports in a thread-safe manner.  

## Activity Diagram  
The `swapShares` method follows a structured flow:  
1. Validate the buyer's ownership of shares.  
2. Validate the availability of desired shares.  
3. Execute the swap transaction with appropriate error handling.  

## Sequence Diagrams  
A NYK admin listing shares available across all servers:  
1. The NYK admin sends a request to the NYK server to list available shares of type "Bonus."  
2. The NYK server forwards this request to all other servers via UDP.  
3. All servers respond, and the NYK server consolidates and returns the result to the admin.  

## Crucial Part of the Solution  
- Ensuring **atomicity** in `swapShares` operations was a significant challenge.  
- The swap operation is only valid if:  
  - The buyer owns the old shares.  
  - The new shares are available.  
- The shares might be distributed across different servers, requiring careful coordination and error handling.  

## Test Cases  
### Basic Functionality Tests  
1. Successful swap - Both shares on a local server with sufficient quantities.  
2. Successful swap - Old share on local server, new share on remote server.  
3. Successful swap - Old share on remote server, new share on local server.  
4. Successful swap - Both shares on different remote servers.  

### Validation Tests  
5. Buyer does not own any of the old shares.  
6. Not enough new shares available for the swap.  

### Server Communication Tests  
7. Remote server for old share is unreachable/times out.  
8. Remote server for new share is unreachable/times out.  
9. Remote server returns an invalid response format.  

### Edge Cases  
10. Swap exactly the same amount of shares as available.  
11. Same share ID but different share types.  
12. Attempting to swap shares with identical IDs and types.  
13. Concurrent swap requests affecting the same shares.  

### Boundary Tests  
14. Zero shares requested for swap (invalid input).  
15. Null or empty share IDs or types.  
16. Invalid share types that don't exist in the system.  
17. Invalid `buyerID` format or non-existent buyer.  

---
This document serves as a README for the **Distributed Share Management System (CORBA)** implementation, covering system architecture, key functionalities, and test cases.

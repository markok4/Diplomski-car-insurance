import { beforeAll, afterEach, afterAll } from 'vitest';
import { server } from './mocks/server';
import { cleanup } from "@testing-library/react"
import "@testing-library/jest-dom/vitest"

// Establish API mocking before all tests
beforeAll(() => server.listen());

// Reset any runtime request handlers we may add during the tests
afterEach(() => {
	server.resetHandlers()
	// runs a clean after each test case (e.g. clearing jsdom)
	cleanup()
})

// Clean up after the tests are finished
afterAll(() => server.close());

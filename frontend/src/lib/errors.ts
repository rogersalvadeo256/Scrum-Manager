import axios from 'axios';

type ProblemDetail = {
  detail?: string;
  message?: string;
  title?: string;
};

export function getErrorMessage(error: unknown) {
  if (axios.isAxiosError<ProblemDetail>(error)) {
    return (
      error.response?.data?.detail ??
      error.response?.data?.message ??
      error.response?.data?.title ??
      error.message
    );
  }

  if (error instanceof Error) {
    return error.message;
  }

  return 'Ocorreu um erro inesperado.';
}

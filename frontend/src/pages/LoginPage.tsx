import { Button } from "@/components/common/Button";
import Container from "@/components/common/Container";
import Form from "@/components/common/Form";
import Input from "@/components/common/Input";
import { Toaster } from "@/components/common/toaster";
import { useAccountAuthContext } from "@/context/AuthContext";
import { useToast } from "@/hooks/use-toast";
import LoadingLayout from "@/layouts/LoadingLayout";
import { backendAddress, queryOptions } from "@/lib/constants";
import { loginAccountSchema } from "@/schemas/accountSchema";
import { TLoginAccount } from "@/types/account";
import { TDefaultResponseBody } from "@/types/response";
import { zodResolver } from "@hookform/resolvers/zod";
import { useQueryClient } from "@tanstack/react-query";
import axios from "axios";
import { useEffect } from "react";
import { useForm } from "react-hook-form";
import { useNavigate } from "react-router";

const LoginPage = () => {
    const { account, isLoading } = useAccountAuthContext();
    const {
        register,
        handleSubmit,
        formState: { isSubmitting, errors },
    } = useForm<TLoginAccount>({
        resolver: zodResolver(loginAccountSchema),
        reValidateMode: "onBlur",
    });
    const queryClient = useQueryClient();
    const toast = useToast();
    const navigate = useNavigate();

    useEffect(() => {
        if (account) navigate("/dashboard");
    });

    function loginHandler(formData: TLoginAccount) {
        toast.toast({
            title: "Processing",
            description: "Logging in. Please wait",
            variant: "default",
        });
        axios
            .post(`${backendAddress}/api/v1/auth/login`, formData, {
                withCredentials: true,
                validateStatus: (status) => {
                    return (status >= 200 && status < 300) || status === 401;
                },
                timeout: 1500,
            })
            .then(({ data }: { data: TDefaultResponseBody }) => {
                if (data.status === 401) {
                    toast.toast({
                        title: "Wrong credentials",
                        description: "Invalid Email/Password combination",
                        variant: "destructive",
                    });
                    return;
                }
                if (data?.status >= 300) {
                    toast.toast({
                        title: "Error",
                        description: data?.message,
                        variant: "destructive",
                    });
                    return;
                }
                queryClient.invalidateQueries({
                    queryKey: queryOptions.account.queryKey,
                });
            })
            .catch((err) => {
                console.error(err);
                toast.toast({
                    title: "Error",
                    description:
                        "An unknown error occured. Please try again later.",
                    variant: "destructive",
                });
            });
    }

    return isLoading ? (
        <LoadingLayout />
    ) : (
        <Container className="text-left">
            <Form className="px-0" onSubmit={handleSubmit(loginHandler)}>
                <div>
                    <p className="large">EduHub</p>
                    <h2>Login</h2>
                </div>
                <Input
                    {...register("email")}
                    type="email"
                    id="email"
                    placeholder="Email"
                    label="Email"
                    errorLabel={errors.email && errors.email.message}
                />
                <Input
                    {...register("password")}
                    type="password"
                    id="password"
                    placeholder="Password"
                    label="Password"
                    errorLabel={errors.password && errors.password.message}
                />
                <Button type="submit" disabled={isSubmitting}>
                    Submit
                </Button>
            </Form>
            <Toaster />
        </Container>
    );
};

export default LoginPage;
